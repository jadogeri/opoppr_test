import { describe, expect, it } from 'vitest';
import reducer, { signedIn, signedOut } from './authSlice';

describe('auth slice', () => {
  it('stores an access token and user after sign in', () => {
    const state = reducer(
      undefined,
      signedIn({
        accessToken: 'token',
        user: { username: 'OPAADMIN', role: 'ADMIN' },
      }),
    );
    expect(state).toEqual({
      accessToken: 'token',
      user: { username: 'OPAADMIN', role: 'ADMIN' },
    });
  });

  it('clears the session after sign out', () => {
    const state = reducer(
      { accessToken: 'token', user: { username: 'OPAADMIN', role: 'ADMIN' } },
      signedOut(),
    );
    expect(state).toEqual({ accessToken: null, user: null });
  });
});