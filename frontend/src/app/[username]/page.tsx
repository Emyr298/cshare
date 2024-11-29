import { ProfileModule } from "@/components";

export default async function UserProfile({
  params,
}: {
  params: Promise<{ username: string }>;
}) {
  const usernameUrl = decodeURIComponent((await params).username);

  if (usernameUrl.length == 0 || usernameUrl[0] !== "@") {
    return <div>Not Found</div>;
  }

  const username = usernameUrl.substring(1);
  return <ProfileModule username={username} />;
}
