

export interface Fountain {
    readonly id: string;
    readonly tObject: string;
    readonly modele: string;
    readonly numVoie: string;
    readonly voie: string;
    readonly commune: string;
    readonly disponibility: boolean;
    fav: boolean;
    readonly geoPoint2d: number[];
  }