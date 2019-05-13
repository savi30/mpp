import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BookListComponent} from './books/book-list/book-list.component';
import {BookComponent} from './books/book/book.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {BookService} from './books/service/book.service';
import {LogsService} from './logs/service/logs.service';
import {UsersService} from './users/service/users.service';
import {UserComponent} from './users/user/user.component';
import {LogEntryComponent} from './logs/log-entry/log-entry.component';
import {UserListComponent} from './users/user-list/user-list.component';
import {LogListComponent} from './logs/log-list/log-list.component';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatGridListModule, MatInputModule,
  MatListModule, MatPaginatorModule,
  MatTabsModule
} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ReportsService} from "./logs/service/reports.service";

@NgModule({
  declarations: [
    AppComponent,
    BookListComponent,
    BookComponent,
    UserComponent,
    LogEntryComponent,
    UserListComponent,
    LogListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatGridListModule,
    MatListModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule
  ],
  providers: [BookService, LogsService, UsersService, ReportsService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
