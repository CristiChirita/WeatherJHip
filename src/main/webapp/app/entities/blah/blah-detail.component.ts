import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBlah } from 'app/shared/model/blah.model';

@Component({
    selector: 'jhi-blah-detail',
    templateUrl: './blah-detail.component.html'
})
export class BlahDetailComponent implements OnInit {
    blah: IBlah;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ blah }) => {
            this.blah = blah;
        });
    }

    previousState() {
        window.history.back();
    }
}
