import { describe, expect, it } from 'vitest';
import type { Lat5Row } from './index';

describe('shared contracts', () => {
  it('keeps the LAT5 row shape usable by both clients', () => {
    const row: Lat5Row = {
      id: null,
      section: 1,
      category: '10',
      propertyType: 'Furniture',
      description: 'Office furniture',
      acquisitionCost: 100,
      priorYearCost: null,
    };
    expect(row.section).toBe(1);
    expect(row.acquisitionCost).toBe(100);
  });
});