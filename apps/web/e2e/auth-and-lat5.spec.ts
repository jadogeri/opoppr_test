import { expect, test } from '@playwright/test';

test('taxpayer can sign in and open a LAT5 filing', async ({ page }) => {
  await page.route('**/api/v1/auth/login', async (route) => {
    await route.fulfill({
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify({ accessToken: 'e2e-token', username: 'OPAADMIN', role: 'ADMIN' }),
    });
  });
  await page.route('**/api/v1/dashboard', async (route) => {
    await route.fulfill({
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify({
        displayName: 'OPAADMIN',
        counts: { IN_PROGRESS: 1, SUBMITTED: 1, DRAFT: 0, CLOSED: 0 },
        forms: [{
          id: 1,
          title: '2025 Personal Property Return',
          filingYear: 2025,
          billNumber: 'DEMO-0001',
          status: 'IN_PROGRESS',
          lastModifiedDate: null,
        }],
      }),
    });
  });

  await page.goto('/login');
  await page.getByLabel(/tax bill number/i).fill('OPAADMIN');
  await page.getByLabel(/^pin$/i).fill('123456');
  await page.getByRole('button', { name: /sign in/i }).click();

  await expect(page.getByRole('heading', { name: /keep your filings moving/i })).toBeVisible();
  await page.getByRole('link', { name: /open lat5/i }).click();
  await expect(page.getByRole('heading', { name: /demo taxpayer/i })).toBeVisible();
});