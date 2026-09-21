import type { Metadata } from 'next';
import 'handsontable/dist/handsontable.full.min.css';
import './globals.css';
import { Providers } from '@/components/Providers';

export const metadata: Metadata = {
  title: 'OPOPPR | Personal Property Reporting',
  description: 'Orleans Parish Online Personal Property Reporting',
};

export default function RootLayout({ children }: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="en">
      <body>
        <Providers>{children}</Providers>
      </body>
    </html>
  );
}