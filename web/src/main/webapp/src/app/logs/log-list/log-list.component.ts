import {Component, OnInit} from '@angular/core';
import {Log} from '../../shared/domain/domain';
import {LogsService} from "../service/logs.service";

@Component({
  selector: 'app-log-list',
  templateUrl: './log-list.component.html',
  styleUrls: ['./log-list.component.css']
})
export class LogListComponent implements OnInit {
  public logs: Log[];
  public selectedEntry: Log;

  constructor(private logService: LogsService) {
  }

  ngOnInit() {
    this.loadData();
  }

  private async loadData() {
    this.logs = await this.logService.findAll().toPromise();
  }

  async delete() {
    if (this.selectedEntry) {
      await this.logService.delete(this.selectedEntry.id).toPromise();
      this.selectedEntry = null;
      this.loadData();
    }
  }

  selectionChange(event: Log) {
    this.selectedEntry = event;
  }
}
