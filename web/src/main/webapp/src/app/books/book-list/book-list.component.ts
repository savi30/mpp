import {Component, OnInit, ViewChild} from '@angular/core';
import {BookService} from "../service/book.service";
import {Book} from "../../shared/domain/domain";
import {MatPaginator, PageEvent} from "@angular/material";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  @ViewChild('paginator')
  private paginator: MatPaginator;

  public books: Book[] = [];
  public selectedBook: Book = new Book();
  public pageSize = 2;
  public currentPage: Book[] = [];


  constructor(private bookService: BookService) {
  }

  ngOnInit() {
    this.loadData(0, this.pageSize);
  }

  private async loadData(start: number, end: number) {
    this.books = await this.bookService.findAll().toPromise();
    this.getCurrentPage(start, end)
  }

  async delete() {
    if (this.selectedBook) {
      await this.bookService.delete(this.selectedBook.id).toPromise();
      this.selectedBook = new Book();
      this.loadData(this.paginator.pageIndex * this.pageSize, this.paginator.pageIndex * this.pageSize + this.pageSize);
    }
  }

  async save() {
    if (this.selectedBook) {
      if (this.books.map(user => user.id).includes(this.selectedBook.id)) {
        await this.bookService.update(this.selectedBook).toPromise();
      } else {
        this.selectedBook.id = Date.now().toString();
        await this.bookService.save(this.selectedBook).toPromise();
      }
      this.loadData(this.paginator.pageIndex * this.pageSize, this.paginator.pageIndex * this.pageSize + this.pageSize);
    }
  }

  selectionChange(event: Book) {
    this.selectedBook = event;
  }

  pageChanged(event: PageEvent) {
    this.getCurrentPage(event.pageSize * event.pageIndex, event.pageIndex * event.pageSize + event.pageSize);
  }

  private getCurrentPage(start: number, end: number) {
    this.currentPage = this.books.slice(start, end);
  }
}
