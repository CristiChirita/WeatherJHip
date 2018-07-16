import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITemperature } from 'app/shared/model/temperature.model';
import { TemperatureService } from './temperature.service';

@Component({
    selector: 'jhi-temperature-delete-dialog',
    templateUrl: './temperature-delete-dialog.component.html'
})
export class TemperatureDeleteDialogComponent {
    temperature: ITemperature;

    constructor(
        private temperatureService: TemperatureService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.temperatureService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'temperatureListModification',
                content: 'Deleted an temperature'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-temperature-delete-popup',
    template: ''
})
export class TemperatureDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ temperature }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TemperatureDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.temperature = temperature;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
