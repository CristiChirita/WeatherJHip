import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITestEntity } from 'app/shared/model/test-entity.model';
import { TestEntityService } from './test-entity.service';

@Component({
    selector: 'jhi-test-entity-delete-dialog',
    templateUrl: './test-entity-delete-dialog.component.html'
})
export class TestEntityDeleteDialogComponent {
    testEntity: ITestEntity;

    constructor(private testEntityService: TestEntityService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.testEntityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'testEntityListModification',
                content: 'Deleted an testEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-test-entity-delete-popup',
    template: ''
})
export class TestEntityDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ testEntity }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TestEntityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.testEntity = testEntity;
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
