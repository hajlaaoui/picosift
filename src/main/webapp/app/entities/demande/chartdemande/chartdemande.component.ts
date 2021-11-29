import { Component, OnInit } from '@angular/core';
import { Chart,registerables } from 'chart.js';
import { Demande } from '../demande.model';
import { DemandeService } from '../service/demande.service';


@Component({
  selector: 'jhi-chartdemande',
  templateUrl: './chartdemande.component.html',
  styleUrls: ['./chartdemande.component.scss'],
})
export class ChartdemandeComponent implements OnInit {
  chart:any = [];
  resultat:any;
  nbaccepter : any


constructor(private service : DemandeService){
  Chart.register(...registerables);
}






ngOnInit():void{

this.service.nbaccepter().subscribe((res) => {
  this.resultat = res ;

})

  this.chart=document.getElementById('myChart');
  Chart.register(...registerables);
  this.loadChart();
}
loadChart():void{




this.chart =new Chart("myChart",{

  type: 'polarArea',
  data: {
     labels: [
    'refuser',
    'accepter',
    'en cours',
  ],
  datasets: [{
    label: 'My First Dataset',
    data:[this.resultat ,4,5] ,

    backgroundColor: [
      'rgb(255, 99, 132)',
      'rgb(75, 192, 192)',
      'rgb(255, 205, 86)',
      'rgb(201, 203, 207)',
      'rgb(54, 162, 235)'
    ]
  }]
  },

});





}
}
