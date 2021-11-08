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
  import { FountainService } from './FountainService';
  import { Fountain } from './Fountain';
//  import { fountainDto } from './fountainDto';
  
  @Controller('/fountains')
  export class FountainController {
    constructor(private readonly fountainService: FountainService) {}
  
    @Get()
    public getAllfountains(@Query('author') author: string): Fountain[] {
      return !!author
        ? this.fountainService.getFountainsOf(author)
        : this.fountainService.getAllFountains();
    }
  
    /*@Post()
    public createfountain(@Body() fountainToCreate: FountainDto): Fountain {
      this.fountainService.addFountain(fountainToCreate);
      return this.fountainService.getFountain(fountainToCreate.title);
    }*/
  
    /*@Get(':title')
    public getfountainWithTitle(@Param('title') fountainTitle: string): Fountain {
      return this.fountainService.getFountain(fountainTitle);
    }*/
  
    @Delete(':title')
    public deletefountain(@Param('title') fountainTitle: string): void {
      return this.fountainService.deleteFountain(fountainTitle);
    }
  
    /*@Post('search')
    @HttpCode(200)
    public searchByAuthorAndTitle(@Body() query: { term: string }): Fountain[] {
      return this.fountainService.searchByAuthorAndTitle(query.term);
    }*/
  }
  