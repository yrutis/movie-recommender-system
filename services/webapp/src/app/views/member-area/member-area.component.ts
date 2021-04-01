import { Component, OnInit } from '@angular/core';
import {MemberService} from '../../service/member.service';

@Component({
  selector: 'app-member-area',
  templateUrl: './member-area.component.html',
  styleUrls: ['./member-area.component.scss']
})
export class MemberAreaComponent implements OnInit {

  constructor(private memberService: MemberService) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.memberService.logout();
  }
}
