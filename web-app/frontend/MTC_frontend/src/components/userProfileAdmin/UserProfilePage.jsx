import Sidebar from "@/components/layout/Sidebar";
import Header from "@/components/layout/Header";
import ProfileCard from "@/components/profile/ProfileCard";
import PersonalInfoCard from "@/components/profile/PersonalInfoCard";
import AddressCard from "@/components/profile/AddressCard";
import Link from "next/link";

export default function UserProfilePage() {
  return (
    <div className="flex min-h-screen w-full bg-gray-100/40 dark:bg-gray-800/40">
      <Sidebar />
      <div className="flex flex-1 flex-col">
        <Header />
        <main className="flex flex-1 flex-col gap-4 p-4 md:gap-8 md:p-6">
          <div className="flex items-center justify-between">
            <h1 className="text-2xl font-semibold">Profile</h1>
            <div className="flex items-center gap-2 text-sm text-gray-500 dark:text-gray-400">
              <Link href="#" className="hover:underline">Home</Link>
              <span>/</span>
              <span>Profile</span>
            </div>
          </div>
          <ProfileCard />
          <PersonalInfoCard />
          <AddressCard />
        </main>
      </div>
    </div>
  );
}
