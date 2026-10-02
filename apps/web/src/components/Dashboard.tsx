'use client';

import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { useDispatch } from 'react-redux';
import { signedOut } from '@/store/authSlice';
import { useGetDashboardQuery } from '@/store/apiSlice';

export function Dashboard() {
  const router = useRouter();
  const dispatch = useDispatch();
  const { data, isLoading, isError } = useGetDashboardQuery();

  function signOut() {
    window.localStorage.removeItem('opoppr.accessToken');
    window.localStorage.removeItem('opoppr.user');
    dispatch(signedOut());
    router.push('/login');
  }

  if (isLoading) {
    return <main className="content"><p className="muted">Loading dashboard…</p></main>;
  }

  if (isError || !data) {
    return (
      <main className="auth-shell">
        <section className="auth-card">
          <div className="eyebrow">OPOPPR</div>
          <h1>Sign in to continue</h1>
          <p className="muted">Your reporting workspace is protected.</p>
          <Link className="button" href="/login">Go to sign in</Link>
        </section>
      </main>
    );
  }

  return (
    <div className="shell">
      <header className="topbar">
        <Link href="/" className="brand">
          OPOPPR
          <small>Online personal property reporting</small>
        </Link>
        <button className="button ghost" onClick={signOut}>Sign out</button>
      </header>
      <main className="content">
        <section className="hero">
          <div>
            <div className="eyebrow">Taxpayer workspace</div>
            <h1>Keep your filings moving.</h1>
            <p className="muted">
              Review a return, update its asset schedule, and submit it when everything is ready.
            </p>
          </div>
        </section>
        <section className="stats" aria-label="Filing counts">
          {['IN_PROGRESS', 'SUBMITTED', 'DRAFT', 'CLOSED'].map((status) => (
            <div className="card stat" key={status}>
              <span>{status.replace('_', ' ')}</span>
              <strong>{data.counts[status as keyof typeof data.counts] ?? 0}</strong>
            </div>
          ))}
        </section>
        <section className="card table-card">
          <div className="card-heading">
            <div>
              <div className="eyebrow">Your returns</div>
              <h2>Personal property filings</h2>
            </div>
          </div>
          <table className="form-table">
            <thead>
              <tr><th>Filing</th><th>Tax year</th><th>Bill number</th><th>Status</th><th /></tr>
            </thead>
            <tbody>
              {data.forms.map((form) => (
                <tr key={form.id}>
                  <td><strong>{form.title}</strong></td>
                  <td>{form.filingYear}</td>
                  <td>{form.billNumber}</td>
                  <td><span className={`status status-${form.status}`}>{form.status.replace('_', ' ')}</span></td>
                  <td>
                    <Link className="button secondary" href={`/forms/${form.id}/lat5`}>
                      Open LAT5
                    </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      </main>
    </div>
  );
}