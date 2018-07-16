export interface ITemperature {
    id?: number;
    continent?: string;
    minTemperature?: number;
    city?: string;
    country?: string;
    maxTemperature?: number;
    cityMax?: string;
    countryMax?: string;
}

export class Temperature implements ITemperature {
    constructor(
        public id?: number,
        public continent?: string,
        public minTemperature?: number,
        public city?: string,
        public country?: string,
        public maxTemperature?: number,
        public cityMax?: string,
        public countryMax?: string
    ) {}
}
