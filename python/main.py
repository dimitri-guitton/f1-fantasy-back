import json
import time

import requests
from bs4 import BeautifulSoup

BASE_URL = "https://www.formula1.com"
short_logos = []


def get_teams():
    """Récupère les URLs de détail de chaque équipe depuis la page principale."""
    teams_url = f"{BASE_URL}/en/teams"
    response = requests.get(teams_url)
    if response.status_code != 200:
        print(f"Erreur lors de la récupération de la page des équipes: {response.status_code}")
        return []
    soup = BeautifulSoup(response.content, "lxml")

    team_links = set()
    # Ici, on parcourt tous les liens qui contiennent '/en/teams/'
    for a in soup.find_all("a", href = True):
        href = a["href"]
        # On vérifie que le lien correspond à une page d'équipe (et pas à une sous-section)
        if href.startswith("/en/teams/") and len(href.split("/")) == 4:
            # Récupération du logo de l'équipe
            img_tag = a.find("img")
            team_logo = img_tag.get("src") if img_tag else None
            team_name = img_tag.get("alt") if img_tag else None

            short_logos.append({team_name: team_logo})

            full_link = BASE_URL + href
            team_links.add(full_link)
    return list(team_links)


def scrape_team_details(team_url):
    """Scrappe les informations d'une équipe et de ses pilotes à partir de sa page dédiée."""
    response = requests.get(team_url)
    if response.status_code != 200:
        print(f"Erreur lors de la récupération de la page de l'équipe {team_url}: {response.status_code}")
        return None
    soup = BeautifulSoup(response.content, "lxml")
    team_data = {}

    title_tag = soup.find("h1")
    if title_tag:
        team_data["name"] = title_tag.get_text(strip = True)
    else:
        team_data["name"] = "Nom inconnu"

    # logo_tag = soup.find("img")
    # Image avec en alt le nom de l'équipe
    logo_tag = soup.find("img", alt = team_data["name"])
    team_data["full_logo"] = logo_tag.get("src") if logo_tag else None
    short_logo = [logo for logo in short_logos if team_data["name"] in logo.keys()]
    team_data["short_logo"] = short_logo[0][team_data["name"]] if short_logo else None

    drivers = []

    drivers_section = soup.find("div", class_ = "grid gap-micro f1-grid grid-cols-2 bg-brand-offWhite")
    if drivers_section:
        driver_cards = drivers_section.find_all("figure")
        for card in driver_cards:
            driver = {}
            number_tag = card.find("figcaption").find("div").find_all("p")[0]
            driver["number"] = number_tag.get_text(strip = True) if number_tag else "-"

            name_tag = card.find("figcaption").find("div").find_all("p")[1]
            driver["name"] = name_tag.get_text(strip = True) if name_tag else "Nom inconnu"

            img_tag = card.find("img")
            driver["image"] = img_tag.get("src") if img_tag else None

            drivers.append(driver)
    else:
        print(f"Aucune section de pilotes trouvée sur {team_url}")

    team_data["drivers"] = drivers
    return team_data


def main():
    teams = get_teams()
    if not teams:
        print("Aucune équipe trouvée.")
        return

    all_data = []
    for team_url in teams:
        print(f"Scraping de l'équipe : {team_url}")
        team_data = scrape_team_details(team_url)
        if team_data:
            all_data.append(team_data)
        # Petit délai pour éviter de surcharger le serveur
        time.sleep(1)

    # Sauvegarde des données dans un fichier JSON
    with open("f1_teams_drivers.json", "w", encoding = "utf-8") as f:
        json.dump(all_data, f, ensure_ascii = False, indent = 4)

    print("Les données ont été sauvegardées dans f1_teams_drivers.json")


if __name__ == "__main__":
    main()
