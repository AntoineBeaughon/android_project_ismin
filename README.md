Part of Android & Web Development - ISMIN 2021

Course followed by students of Mines St Etienne, ISMIN - M2 Computer Science.

[![Mines St Etienne](./logo.png)](https://www.mines-stetienne.fr/)

Antoine Beaughon - Clémence Jampy

Projet Android 2021 : conception d'une API de base de donnée et d'une application android affichant la base de donnée


# Fonctionnalités

- Affiche la liste des fontaines
- Cliquer sur un élément de la liste permet de faire afficher en détail les informations relatives à celle-ci
- Un bouton refresh permet d'actualiser la base de donnée
- Un bouton "infos" ouvre une page présentant notre projet et son cadre

# Architecture côté API

On crée deux types de données : 
- ExternalFountain contient la donnée sous forme brute, tel que reçu dans la base de donnée
- Fountain désigne la donnée adaptée pour notre utilisation

Les autres fichiers sont :
- app.service : gère le traitement des données
- app.controller : définit les requêtes effectuables
- app.module : assure la liaison entre le controller et le service
- main : écoute et effectue les requêtes

# Architecture côté Android

Une partie du code côté Android est consacré à la récupération et au traitement des données de l'API :
- Fountain : L'équivalent KotLin du type "Fountain" défini côté API
- FountainShelf : classe gérant un ensemble de Fountain
- FountainAdapter : classe permettant de traiter les données pour qu'elles puissent être affichées dans une liste dans l'application
- FountainService : définit les différentes requêtes envoyables à l'API concernant les fontaines
- UserService : définit les différentes requêtes envoyables à l'API concernant les favoris

On affiche ces données à l'aide de deux activities et deux fragments :
- MainActivity : l'activité principale qui affiche les boutons et les différents fragments
- FountainListFragment : le fragment contenant la liste des fontaines
- InfosFragment : le fragment contenant les informations sur le projet
- DetailActivity : la deuxième activité qui affiche les détails d'une fontaine lorsqu'on clique sur celle-ci

# Liens utiles

Lien de téléchargement de la base de donnée : https://data.ohttps://data.opendatasoft.com/explore/dataset/fontaines-a-boire@parisdata/download/?format=geojson&timezone=Europe/Berlin&lang=frpendatasoft.com/explore/dataset/fontaines-a-boire@parisdata/download/?format=geojson&timezone=Europe/Berlin&lang=fr

Lien vers le site dont est extraite la base de donnée : https://data.opendatasoft.com/explore/dataset/fontaines-a-boire%40parisdata/information/?disjunctive.type_objet&disjunctive.modele&disjunctive.commune&disjunctive.dispo

Lien vers le server clevercloud hébergeant l'API : https://console.clever-cloud.com/organisations/orga_693c81f4-6d20-46f3-901b-29afc84a379d/applications/app_b3e19c6c-55bd-4e27-bd54-47e255a79c08
