import { Lat5Editor } from '@/components/Lat5Editor';

export default async function Lat5Page({
  params,
}: {
  params: Promise<{ formId: string }>;
}) {
  const { formId } = await params;
  return <Lat5Editor formId={Number(formId)} />;
}