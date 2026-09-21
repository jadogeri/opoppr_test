import React from 'react';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { Provider } from 'react-redux';
import { beforeEach, describe, expect, it, vi } from 'vitest';
import { makeStore } from '@/store/store';
import { LoginForm } from './LoginForm';

const mockLogin = vi.fn();

vi.mock('@/store/apiSlice', async () => {
  const actual = await vi.importActual<typeof import('@/store/apiSlice')>('@/store/apiSlice');
  return {
    ...actual,
    useLoginMutation: () => [mockLogin, { isLoading: false, error: null }],
  };
});

vi.mock('next/navigation', () => ({
  useRouter: () => ({ push: vi.fn() }),
}));

describe('LoginForm integration', () => {
  beforeEach(() => {
    mockLogin.mockReset();
    window.localStorage.clear();
  });

  it('submits credentials and persists the returned session', async () => {
    mockLogin.mockReturnValue({
      unwrap: () => Promise.resolve({ accessToken: 'signed-token', username: 'OPAADMIN', role: 'ADMIN' }),
    });

    render(
      <Provider store={makeStore()}>
        <LoginForm />
      </Provider>,
    );

    fireEvent.change(screen.getByLabelText(/tax bill number/i), { target: { value: 'OPAADMIN' } });
    fireEvent.change(screen.getByLabelText(/^pin$/i), { target: { value: '123456' } });
    fireEvent.click(screen.getByRole('button', { name: /sign in/i }));

    await waitFor(() => expect(mockLogin).toHaveBeenCalledWith({ billNumber: 'OPAADMIN', pin: '123456' }));
    expect(window.localStorage.getItem('opoppr.accessToken')).toBe('signed-token');
  });
});