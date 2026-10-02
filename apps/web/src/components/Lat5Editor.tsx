'use client';

import Link from 'next/link';
import { useMemo, useState } from 'react';
import { HotTable } from '@handsontable/react';
import 'handsontable/dist/handsontable.full.min.css';
import type { Lat5Row } from '@opoppr/contracts';
import { useGetLat5Query, useUpdateLat5Mutation } from '@/store/apiSlice';

type GridRow = [
  number | null,
  number,
  string,
  string,
  string,
  number | null,
  number | null,
];

const headers = ['Id', 'Section', 'Category', 'Property type', 'Description', 'Acquisition cost', 'Prior year cost'];

function flattenRows(sections: Record<number, Lat5Row[]>): GridRow[] {
  return Object.values(sections).flat().map((row) => [
    row.id,
    row.section,
    row.category,
    row.propertyType,
    row.description,
    row.acquisitionCost,
    row.priorYearCost,
  ]);
}

function toRows(rows: GridRow[]): Lat5Row[] {
  return rows
    .filter((row) => row[2] || row[3] || row[4] || row[5] !== null || row[6] !== null)
    .map((row) => ({
      id: row[0],
      section: Number(row[1]) || 1,
      category: String(row[2] ?? ''),
      propertyType: String(row[3] ?? ''),
      description: String(row[4] ?? ''),
      acquisitionCost: row[5] === null || Number.isNaN(Number(row[5])) ? null : Number(row[5]),
      priorYearCost: row[6] === null || Number.isNaN(Number(row[6])) ? null : Number(row[6]),
    }));
}

export function Lat5Editor({ formId }: { formId: number }) {
  const { data, isLoading, isError } = useGetLat5Query(formId);
  const [updateLat5, { isLoading: isSaving, isSuccess }] = useUpdateLat5Mutation();
  const [gridRows, setGridRows] = useState<GridRow[] | null>(null);
  const initialRows = useMemo(() => (data ? flattenRows(data.sections) : []), [data]);
  const rows = gridRows ?? initialRows;

  async function save() {
    await updateLat5({ formId, rows: toRows(rows) }).unwrap();
    setGridRows(null);
  }

  if (isLoading) return <main className="content"><p className="muted">Loading filing…</p></main>;
  if (isError || !data) return <main className="content"><div className="error">This filing could not be loaded.</div></main>;

  return (
    <div className="shell">
      <header className="topbar">
        <Link href="/" className="brand">OPOPPR<small>LAT5 filing editor</small></Link>
        <Link href="/" className="button ghost">Back to dashboard</Link>
      </header>
      <main className="content">
        <section className="editor-header">
          <div>
            <div className="eyebrow">Tax year {data.filingYear}</div>
            <h1>{data.ownerName}</h1>
            <p className="muted">Edit the asset schedule. Right-click a row for insert, delete, undo, and redo actions.</p>
          </div>
          <div className="editor-actions">
            <button className="button" onClick={save} disabled={isSaving}>
              {isSaving ? 'Saving…' : 'Save filing'}
            </button>
          </div>
        </section>
        {isSuccess ? <p className="helper" role="status">Filing saved.</p> : null}
        <section className="card sheet-card" aria-label="LAT5 property asset spreadsheet">
          <HotTable
            data={rows}
            colHeaders={headers}
            rowHeaders
            stretchH="all"
            height="520"
            licenseKey="non-commercial-and-evaluation"
            contextMenu={{
              items: {
                row_above: { name: 'Insert row above' },
                row_below: { name: 'Insert row below' },
                remove_row: { name: 'Delete row' },
                undo: { name: 'Undo' },
                redo: { name: 'Redo' },
                alignment: {},
              },
            }}
            columns={[
              { type: 'numeric', readOnly: true },
              { type: 'numeric', numericFormat: { pattern: '0' } },
              { type: 'text' },
              { type: 'text' },
              { type: 'text' },
              { type: 'numeric', numericFormat: { pattern: '$0,0' } },
              { type: 'numeric', numericFormat: { pattern: '$0,0' } },
            ]}
            hiddenColumns={{ columns: [0], indicators: false }}
            afterChange={(changes, source) => {
              if (source !== 'loadData' && changes) {
                setGridRows(rows as GridRow[]);
              }
            }}
          />
          <p className="helper">
            Use the context menu to add rows above or below the selected row. Changes are local until
            you choose Save filing.
          </p>
        </section>
      </main>
    </div>
  );
}