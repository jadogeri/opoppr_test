'use client';

import React, { FormEvent, useState } from 'react';
import { useRouter } from 'next/navigation';
import { useDispatch } from 'react-redux';
import { signedIn } from '@/store/authSlice';
import { useLoginMutation } from '@/store/apiSlice';

export function LoginForm() {
  const router = useRouter();
  const dispatch = useDispatch();
  const [login, { isLoading, error }] = useLoginMutation();
  const [billNumber, setBillNumber] = useState('');
  const [pin, setPin] = useState('');

  async function submit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    try {
      const result = await login({ billNumber, pin }).unwrap();
      const user = { username: result.username, role: result.role };
      window.localStorage.setItem('opoppr.accessToken', result.accessToken);
      window.localStorage.setItem('opoppr.user', JSON.stringify(user));
      dispatch(signedIn({ accessToken: result.accessToken, user }));
      router.push('/');
    } catch {
      // RTK Query exposes the response in the rendered error state.
    }
  }

  return (
    <form onSubmit={submit} aria-label="Sign in">
      <div className="field">
        <label htmlFor="bill-number">Tax bill number</label>
        <input
          id="bill-number"
          name="billNumber"
          value={billNumber}
          onChange={(event) => setBillNumber(event.target.value)}
          placeholder="OPAADMIN"
          autoComplete="username"
          required
        />
      </div>
      <div className="field">
        <label htmlFor="pin">PIN</label>
        <input
          id="pin"
          name="pin"
          value={pin}
          onChange={(event) => setPin(event.target.value)}
          placeholder="123456"
          inputMode="numeric"
          autoComplete="current-password"
          required
        />
      </div>
      {error ? <div className="error" role="alert">The bill number or PIN was not accepted.</div> : null}
      <button className="button" type="submit" disabled={isLoading}>
        {isLoading ? 'Signing in…' : 'Sign in'}
      </button>
    </form>
  );
}