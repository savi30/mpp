import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from '../../shared/domain/domain';
import {UsersService} from "../service/users.service";
import {MatPaginator, PageEvent} from "@angular/material";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  @ViewChild('paginator')
  private paginator: MatPaginator;

  public users: User[] = [];
  public selectedUser: User = new User();
  public pageSize = 3;
  public currentPage: User[] = [];

  constructor(private userService: UsersService) {
  }

  ngOnInit() {
    this.loadData(0, this.pageSize);
  }

  private async loadData(start: number, end: number) {
    this.users = await this.userService.findAll().toPromise();
    this.getCurrentPage(start, end)
  }

  async save() {
    if (this.selectedUser) {
      if (this.users.map(user => user.id).includes(this.selectedUser.id)) {
        await this.userService.update(this.selectedUser).toPromise();
      } else {
        this.selectedUser.id = Date.now().toString();
        await this.userService.save(this.selectedUser).toPromise();
      }
      this.loadData(this.paginator.pageIndex * this.pageSize, this.paginator.pageIndex * this.pageSize + this.pageSize);
    }
  }

  async delete() {
    if (this.selectedUser) {
      await this.userService.delete(this.selectedUser.id).toPromise();
      this.selectedUser = new User();
      this.loadData(this.paginator.pageIndex * this.pageSize, this.paginator.pageIndex * this.pageSize + this.pageSize);
    }
  }

  pageChanged(event: PageEvent) {
    this.getCurrentPage(event.pageSize * event.pageIndex, event.pageIndex * event.pageSize + event.pageSize);
  }

  selectionChange(event: User) {
    this.selectedUser = event;
  }

  clear() {
    this.selectedUser = new User();
  }

  private getCurrentPage(start, end) {
    this.currentPage = this.users.slice(start, end);
  }
}
