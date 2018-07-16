export interface IBlah {
    id?: number;
    location?: string;
    continent?: string;
}

export class Blah implements IBlah {
    constructor(public id?: number, public location?: string, public continent?: string) {}
}
