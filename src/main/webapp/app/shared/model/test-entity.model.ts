export interface ITestEntity {
    id?: number;
}

export class TestEntity implements ITestEntity {
    constructor(public id?: number) {}
}
