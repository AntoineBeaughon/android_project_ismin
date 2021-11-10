import {
    Body,
    Controller,
    Delete,
    Get,
    HttpCode,
    Param,
    Post,
    Query,
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
  
  /*@Post()
  public createfountain(@Body() fountainToCreate: FountainDto): Fountain {
    this.fountainService.addFountain(fountainToCreate);
    return this.fountainService.getFountain(fountainToCreate.title);
  }*/
  
  @Get('/:voie')
  public getfountainWithTitle(@Param('voie') fountainTitle: string): Fountain[] {
    return this.fountainService.getFountainsByStreet(fountainTitle);
  }
  
  @Delete(':id')
  public deletefountain(@Param('id') fountainTitle: string): void {
    return this.fountainService.deleteFountain(fountainTitle);
  }
  
  /*@Post('search')
  @HttpCode(200)
  public searchByAuthorAndTitle(@Body() query: { term: string }): Fountain[] {
    return this.fountainService.searchByAuthorAndTitle(query.term);
  }*/
}
  