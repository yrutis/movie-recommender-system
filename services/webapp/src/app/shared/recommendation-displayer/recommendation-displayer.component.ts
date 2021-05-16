import {AfterViewInit, Component, HostListener, Input, OnInit} from '@angular/core';
import {Movie} from '../../model/movie';
import {zoomInOnEnterAnimation} from 'angular-animations';
import {MemberService} from '../../service/member.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-recommendation-displayer',
  templateUrl: './recommendation-displayer.component.html',
  styleUrls: ['./recommendation-displayer.component.scss'],
  animations: [zoomInOnEnterAnimation({anchor: 'enterAnim'})]
})
export class RecommendationDisplayerComponent implements OnInit, AfterViewInit {

  @Input()
  lastTrainedOn: Date;
  @Input()
  isMember = false;
  @Input()
  recommendations: Movie[];
  filteredRecommendations: Movie[];
  p = 1;
  height = 385;
  trainingAllowed: boolean;

  @HostListener('window:resize', ['$event'])
  onResize(event): void {
    if (document.getElementsByClassName('movie-poster')[0]) {
      this.height = (document.getElementsByClassName('movie-poster')[0] as HTMLElement).offsetWidth * 1.45;
    }
  }

  constructor(private memberService: MemberService, private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.filteredRecommendations = this.recommendations;
    this.trainingAllowed = this.memberService.isTrainingAllowed;
  }

  startTrainingManually(): void {
    this.memberService.startTrainingManually().subscribe(value => {
      this.toastr.success('Training started successfully');
      this.trainingAllowed = this.memberService.isTrainingAllowed;
    }, () => {
      this.toastr.error('Training could not be started successfully');
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      if (document.getElementsByClassName('movie-poster')[0]) {
        this.height = (document.getElementsByClassName('movie-poster')[0] as HTMLElement).offsetWidth * 1.45;
      }
    }, 100);

  }

}
