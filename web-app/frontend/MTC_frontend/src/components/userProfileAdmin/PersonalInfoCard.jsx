// components/profile/PersonalInfoCard.jsx
// import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
// import { Button } from "@/components/ui/button";
// import { EditIcon } from "lucide-react";

import { Card, CardHeader, CardTitle, CardContent } from "../ui/card";
import { Button } from "../ui/button";
import { EditIcon } from "lucide-react";

export default function PersonalInfoCard() {
  return (
    <Card className="p-6">
      <CardHeader className="flex flex-row items-center justify-between space-y-0 p-0">
        <CardTitle className="text-xl font-semibold">Personal Information</CardTitle>
        <Button variant="outline">
          <EditIcon className="mr-2 h-4 w-4" />
          Edit
        </Button>
      </CardHeader>
      <CardContent className="mt-6 grid grid-cols-1 gap-4 p-0 md:grid-cols-2">
        <div>
          <p className="text-sm text-gray-500 dark:text-gray-400">First Name</p>
          <p className="font-medium">Musharof</p>
        </div>
        <div>
          <p className="text-sm text-gray-500 dark:text-gray-400">Last Name</p>
          <p className="font-medium">Chowdhury</p>
        </div>
        <div>
          <p className="text-sm text-gray-500 dark:text-gray-400">Email address</p>
          <p className="font-medium">randomuser@pimjo.com</p>
        </div>
        <div>
          <p className="text-sm text-gray-500 dark:text-gray-400">Phone</p>
          <p className="font-medium">+09 363 398 46</p>
        </div>
        <div className="col-span-full">
          <p className="text-sm text-gray-500 dark:text-gray-400">Bio</p>
          <p className="font-medium">Team Manager</p>
        </div>
      </CardContent>
    </Card>
  );
}
import Modal from "../ui/modal";