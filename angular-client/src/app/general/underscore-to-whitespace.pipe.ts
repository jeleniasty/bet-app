import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'underscoreToWhitespace' })
export class UnderscoreToWhitespacePipe implements PipeTransform {
  transform(value: string | undefined): string | undefined {
    if (value) {
      return value.replace(/_/g, ' ');
    }
    return value;
  }
}
