import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BookListComponent} from "./books/book-list/book-list.component";
import {LogListComponent} from "./logs/log-list/log-list.component";
import {UserListComponent} from "./users/user-list/user-list.component";

const routes: Routes = [
  {
    path: 'books',
    component: BookListComponent
  },
  {
    path: 'users',
    component: UserListComponent
  },
  {
    path: 'logs',
    component: LogListComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
