import { Injectable, Logger, OnModuleInit } from '@nestjs/common';
import { Fountain } from './Fountain';
import { readFile } from 'fs/promises';
import { HttpService } from '@nestjs/axios';
import { ExternalFountain } from './ExternalFountain';
import { firstValueFrom, map } from 'rxjs';

@Injectable()
export class FountainService implements OnModuleInit {
  private readonly fountainStorage = new Map<string, Fountain>();
  private readonly logger = new Logger(FountainService.name); // ?

  constructor(private readonly httpService: HttpService) {}

  async onModuleInit(): Promise<void> {
    const dataset = await readFile(`src/dataset.json`, 'utf8');

    // Read file and then convert content to a Book[]
    const fileFountains = (JSON.parse(dataset) as any[]).map((fountainFromFile) => {
      let isOpen: boolean;
      if(fountainFromFile.disponibility === "OUI"){
        isOpen = true;
      }
      else{
        isOpen = false;
      }
      const convertedFountain: Fountain = {
        id: fountainFromFile.gid,
        tObject: fountainFromFile.type_object,
        modele: fountainFromFile.modele,
        numVoie: fountainFromFile.numVoie,
        voie: fountainFromFile.voie,
        commune: fountainFromFile.commune,
        disponibility: isOpen,
      };
      return convertedFountain;
    });

    // Call external API and then convert content to a Book[]
    const externalFountains = await firstValueFrom(
      this.httpService
        .get<ExternalFountain[]>('https://data.opendatasoft.com/explore/dataset/fontaines-a-boire@parisdata/download/?format=geojson&timezone=Europe/Berlin&lang=fr') //Normalement c'est le bon lien
        .pipe(
          map((response) =>
            response.data.map((externalFountain) => ({
              id: parseInt(externalFountain.gid),
              tObject: externalFountain.type_objet,
              disponibility: (externalFountain.dispo==="OUI" ? true : false),
              modele: externalFountain.modele,
              commune: externalFountain.commune,
              numVoie: typeof(externalFountain.no_voirie_pair)=== undefined ? externalFountain.no_voirie_impair : externalFountain.no_voirie_pair,
              voie: externalFountain.voie,
            })),
          ),
        ),
    );

    // Add all the books
    [...fileFountains, ...externalFountains].forEach((fountain) => this.addFountain(fountain));

    this.logger.log(`There are ${this.fountainStorage.size} books in the storage.`);
  }

  addFountain(fountain: Fountain): void {
    this.fountainStorage.set(fountain.tObject, fountain);
  }

  getFoutain(name: string): Fountain {
    const foundFountain = this.fountainStorage.get(name);

    if (!foundFountain) {
      throw new Error(`Not book found with name ${name}`);
    }

    return foundFountain;
  }

  getFountainsOf(author: string): Fountain[] {
    return this.getAllFountains().filter((fountain) => {
      return fountain.tObject === author;
    });
  }

  getAllFountains(): Fountain[] {
    return Array.from(this.fountainStorage.values()).sort((fountain1, fountain2) =>
      fountain1.tObject.localeCompare(fountain2.tObject),
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
