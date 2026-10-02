import { LoginForm } from '@/components/LoginForm';

export default function LoginPage() {
  return (
    <main className="auth-shell">
      <section className="auth-card">
        <div className="eyebrow">OPOPPR / Orleans Parish</div>
        <h1>Online personal property reporting</h1>
        <p className="muted">Sign in with your tax bill number and six-digit PIN.</p>
        <LoginForm />
      </section>
    </main>
  );
}