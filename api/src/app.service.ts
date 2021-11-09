import { Injectable, Logger, OnModuleInit } from '@nestjs/common';
import { Fountain } from './Fountain';
import { readFile } from 'fs/promises';
import { HttpService } from '@nestjs/axios';
import { ExternalFountain } from './ExternalFountain';
import { firstValueFrom, map } from 'rxjs';

@Injectable()
export class AppService implements OnModuleInit {
  private readonly fountainStorage = new Map<string, Fountain>();
  private readonly logger = new Logger(AppService.name);

  constructor(private readonly httpService: HttpService) {}

  async onModuleInit(): Promise<void> {
    const dataset = await readFile(`src/dataset.json`, 'utf8');

    // Read file and then convert content to a Fountain[]
    //const fileFountains = (JSON.parse(dataset) as any[]).map((fountainFromFile) => {
    /*const fileFountains = [JSON.parse(dataset)].map((fountainFromFile) => {
      const convertedFountain: Fountain = {
        id: fountainFromFile.features.gid,
        tObject: fountainFromFile.features.type_object,
        modele: fountainFromFile.features.modele,
        numVoie: fountainFromFile.features.numVoie,
        voie: fountainFromFile.features.voie,
        commune: fountainFromFile.features.commune,
        geoPoint2d: fountainFromFile.features.geo_point_2d,
        disponibility: (fountainFromFile.features.dispo==="OUI" ? true : false),
        fav: true,
      };
      return convertedFountain;
    });*/

    // Call external API and then convert content to a Book[]
    const externalFountains = await firstValueFrom(
      this.httpService
        .get<ExternalFountain>('https://data.opendatasoft.com/explore/dataset/fontaines-a-boire@parisdata/download/?format=geojson&timezone=Europe/Berlin&lang=fr') //Normalement c'est le bon lien
        .pipe(
          map((response) =>
            response.data.features.map((externalFountain) => ({
              id: externalFountain.properties.gid,
              tObject: externalFountain.properties.type_objet,
              disponibility: (externalFountain.properties.dispo==="OUI" ? true : false),
              modele: externalFountain.properties.modele,
              geoPoint2d: externalFountain.properties.geo_point_2d,
              commune: externalFountain.properties.commune,
              numVoie: typeof(externalFountain.properties.no_voirie_pair)=== undefined ? externalFountain.properties.no_voirie_impair : externalFountain.properties.no_voirie_pair,
              voie: externalFountain.properties.voie,
              fav: false,
            })),
          ),
        ),
    );

    // Add all the books
    //[...fileFountains, ...externalFountains].forEach((fountain) => this.addFountain(fountain));
    [...externalFountains].forEach((fountain) => this.addFountain(fountain));

    this.logger.log(`There are ${this.fountainStorage.size} indexed fountains.`);
  }

  
  addFountain(fountain: Fountain): void {
    this.fountainStorage.set(fountain.tObject, fountain);
  }

  // renvoie au moins une fontaine d'une rue
  /*getFoutain(voie: string): Fountain {
    const foundFountain = this.fountainStorage.get(voie);

    if (!foundFountain) {
      throw new Error(`Not book found with name ${voie}`);
    }

    return foundFountain;
  }*/

  getFountainsByStreet(voie: string): Fountain[] { // renvoie toutes les fontaines d'une rues
    return this.getAllFountains().filter((fountain) => {
      return fountain.voie === voie;
    });
  }

  getFountainsByDistrict(commune: string): Fountain[] { // renvoie toutes les fontaines d'un arrondissement
    return this.getAllFountains().filter((fountain) => {
      return fountain.commune === commune;
    });
  }

  getAllFountains(): Fountain[] { // renvoie la liste des fontaines triées par ID croissant
    return Array.from(this.fountainStorage.values()).sort((fountain1, fountain2) =>
      fountain1.id.localeCompare(fountain2.id),
    );
  }

  getTotalNumberOfFountains(): number {
    return this.fountainStorage.size;
  }

  /*getFountainsPublishedBefore(aDate: string | Date): Fountain[] {
    const dateCriterion = typeof aDate === 'string' ? new Date(aDate) : aDate;

    return this.getAllFountains().filter(
      (fountain) => Fountain.date.getTime() <= dateCriterion.getTime(),
    );
  }*/

  deleteFountain(fountainTitle: string): void {
    this.fountainStorage.delete(fountainTitle);
  }

  /*searchByAuthorAndTitle(term: string): Fountain[] {
    const escapedTerm = term.toLowerCase().trim();

    return this.getAllFountains().filter((fountain) => {
      return (
        fountain.title.toLowerCase().includes(escapedTerm) ||
        fountain.author.toLowerCase().includes(escapedTerm)
      );
    });
  }*/
}
