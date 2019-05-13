import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {User} from '../../shared/domain/domain';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  @Input()
  public user: User;
  @Output()
  public selectionChange: EventEmitter<User> = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

}
