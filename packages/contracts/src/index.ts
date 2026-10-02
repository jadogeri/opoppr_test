export type FormStatus = 'DRAFT' | 'IN_PROGRESS' | 'SUBMITTED' | 'CLOSED';

export interface FormSummary {
  id: number;
  title: string;
  filingYear: number;
  billNumber: string;
  status: FormStatus;
  lastModifiedDate: string | null;
}

export interface DashboardSummary {
  displayName: string;
  forms: FormSummary[];
  counts: Record<FormStatus, number>;
}

export interface Lat5Row {
  id: number | null;
  section: number;
  category: string;
  propertyType: string;
  description: string;
  acquisitionCost: number | null;
  priorYearCost: number | null;
  isDeleted?: boolean;
}

export interface Lat5Form {
  formId: number;
  filingYear: number;
  ownerName: string;
  contactEmail: string | null;
  sections: Record<number, Lat5Row[]>;
}