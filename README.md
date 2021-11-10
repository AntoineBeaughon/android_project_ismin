Part of Android & Web Development - ISMIN 2021

Course followed by students of Mines St Etienne, ISMIN - M2 Computer Science.

[![Mines St Etienne](./logo.png)](https://www.mines-stetienne.fr/)

Antoine Beaughon - Clémence Jampy
Projet Android 2021 : conception d'une API de base de donnée et d'une application android affichant la base de donnée

Lien de téléchargement de la base de donnée : https://data.ohttps://data.opendatasoft.com/explore/dataset/fontaines-a-boire@parisdata/download/?format=geojson&timezone=Europe/Berlin&lang=frpendatasoft.com/explore/dataset/fontaines-a-boire@parisdata/download/?format=geojson&timezone=Europe/Berlin&lang=fr

Lien vers la base de donnée : https://data.opendatasoft.com/explore/dataset/fontaines-a-boire%40parisdata/information/?disjunctive.type_objet&disjunctive.modele&disjunctive.commune&disjunctive.dispo

Lien vers le server clevercloud : https://console.clever-cloud.com/organisations/orga_693c81f4-6d20-46f3-901b-29afc84a379d/applications/app_b3e19c6c-55bd-4e27-bd54-47e255a79c08

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

