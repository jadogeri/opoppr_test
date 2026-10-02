import { createApi } from '@reduxjs/toolkit/query/react';
import type {
  DashboardSummary,
  Lat5Form,
  Lat5Row,
} from '@opoppr/contracts';
import { axiosBaseQuery } from '@/lib/axiosBaseQuery';

type LoginResponse = {
  accessToken: string;
  username: string;
  role: string;
};

export const apiSlice = createApi({
  reducerPath: 'api',
  baseQuery: axiosBaseQuery(),
  tagTypes: ['Dashboard', 'Lat5'],
  endpoints: (builder) => ({
    login: builder.mutation<LoginResponse, { billNumber: string; pin: string }>({
      query: (body) => ({ url: '/api/v1/auth/login', method: 'POST', data: body }),
    }),
    getDashboard: builder.query<DashboardSummary, void>({
      query: () => ({ url: '/api/v1/dashboard' }),
      providesTags: ['Dashboard'],
    }),
    getLat5: builder.query<Lat5Form, number>({
      query: (formId) => ({ url: `/api/v1/forms/${formId}/lat5` }),
      providesTags: (_result, _error, formId) => [{ type: 'Lat5', id: formId }],
    }),
    updateLat5: builder.mutation<Lat5Form, { formId: number; rows: Lat5Row[] }>({
      query: ({ formId, rows }) => ({
        url: `/api/v1/forms/${formId}/lat5`,
        method: 'PUT',
        data: { rows },
      }),
      invalidatesTags: (_result, _error, { formId }) => [{ type: 'Lat5', id: formId }, 'Dashboard'],
    }),
    submitForm: builder.mutation<
      DashboardSummary['forms'][number],
      number
    >({
      query: (formId) => ({
        url: `/api/v1/forms/${formId}/submit`,
        method: 'POST',
      }),
      invalidatesTags: ['Dashboard'],
    }),
  }),
});

export const {
  useLoginMutation,
  useGetDashboardQuery,
  useGetLat5Query,
  useUpdateLat5Mutation,
  useSubmitFormMutation,
} = apiSlice;