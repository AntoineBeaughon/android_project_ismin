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
//  import { fountainDto } from './fountainDto';
  
@Controller('/fountains')
export class AppController {
  constructor(private readonly fountainService: AppService) {}
  
  @Get()
  public getAllfountains(@Query('commune') commune: string): Fountain[] {
    return !!commune
      ? this.fountainService.getFountainsByDistrict(commune)
      : this.fountainService.getAllFountains();
  }
    
  @Get('/:voie')
  public getfountainWithTitle(@Param('voie') fountainTitle: string): Fountain[] {
    return this.fountainService.getFountainsByStreet(fountainTitle);
  }
  
  @Delete(':id')
  public deletefountain(@Param('id') fountainId: string): void {
    return this.fountainService.deleteFountain(fountainId);
  }
  
  @Put(':id')
  public addToFav(@Param('id') fountainId: string): void{
    return this.fountainService.addToFav(fountainId);
  }
}
  