

export interface ExternalFountain {
  features: Array<Feature>,
}
interface Feature{
  type:string,
  geometry:{
    type:string,
    coordinates:number[],
  }
  properties:{
    type_objet: string,
    dispo: string,
    voie: string,
    geo_point_2d: number[],
    no_voirie_pair: string,
    no_voirie_impair: string,
    commune: string,
    debut_ind: string,
    fin_ind: string,
    motif_ind: string,
    gid: string,
    modele: string,
  }
}