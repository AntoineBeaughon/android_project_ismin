import {
    Body,
    Controller,
    Delete,
    Get,
    HttpCode,
    Param,
    Post,
    Query,
    Put,
} from '@nestjs/common';
import { AppService } from './app.service';
import { Fountain } from './Fountain';
  
@Controller('/fountains')
export class AppController {
  constructor(private readonly fountainService: AppService) {}
  
  @Get()
  public getAllfountains(@Query('commune') commune: string): Fountain[] { //récupère toutes les fontaines d'un arrondissement. Si aucun arrondissement n'est précisé, renvoie toute les fontaines
    return !!commune
      ? this.fountainService.getFountainsByDistrict(commune)
      : this.fountainService.getAllFountains();
  }
    
  @Get('/:voie')
  public getfountainWithTitle(@Param('voie') fountainTitle: string): Fountain[] { //renvoie la ou les fontaines présente dans la rue "voie"
    return this.fountainService.getFountainsByStreet(fountainTitle);
  }
  
  @Delete(':id')
  public deletefountain(@Param('id') fountainId: string): void { //supprime une fontaine à partir de son identifiant
    return this.fountainService.deleteFountain(fountainId);
  }
  
  @Put(':id')
  public addToFav(@Param('id') fountainId: string): void{ // ajoute ou retire une fontaine de la liste des favori à partir de son ID
    return this.fountainService.addToFav(fountainId);
  }
}
  