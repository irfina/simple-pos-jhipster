import axios from 'axios';

import { type ICountry } from '@/shared/model/country.model';

const baseApiUrl = 'api/countries';

export default class CountryService {
  public find(isoCode: string): Promise<ICountry> {
    return new Promise<ICountry>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${isoCode}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(isoCode: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${isoCode}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: ICountry): Promise<ICountry> {
    return new Promise<ICountry>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: ICountry): Promise<ICountry> {
    return new Promise<ICountry>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.isoCode}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: ICountry): Promise<ICountry> {
    return new Promise<ICountry>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.isoCode}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
