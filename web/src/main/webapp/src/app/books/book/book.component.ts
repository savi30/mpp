import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Book} from "../../shared/domain/domain";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {
  @Input()
  public book: Book;
  @Output()
  public selectionChange: EventEmitter<Book> = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

}
