import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Log} from '../../shared/domain/domain';

@Component({
  selector: 'app-log-entry',
  templateUrl: './log-entry.component.html',
  styleUrls: ['./log-entry.component.css']
})
export class LogEntryComponent implements OnInit {
  @Input()
  public logEntry: Log;
  @Output()
  public selectionChange: EventEmitter<Log> = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

  public edit() {

  }

  public delete() {

  }
}
