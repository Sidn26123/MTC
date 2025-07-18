// components/profile/AddressCard.jsx
import { Card, CardHeader, CardTitle, CardContent } from "../ui/card";
import { Button } from "../ui/button";
import { EditIcon } from "lucide-react";

export default function AddressCard() {
  return (
    <Card className="p-6">
      <CardHeader className="flex flex-row items-center justify-between space-y-0 p-0">
        <CardTitle className="text-xl font-semibold">Address</CardTitle>
        <Button variant="outline">
          <EditIcon className="mr-2 h-4 w-4" />
          Edit
        </Button>
      </CardHeader>
      <CardContent className="mt-6 grid grid-cols-1 gap-4 p-0 md:grid-cols-2">
        <div>
          <p className="text-sm text-gray-500 dark:text-gray-400">Country</p>
          <p className="font-medium">United States.</p>
        </div>
        <div>
          <p className="text-sm text-gray-500 dark:text-gray-400">City/State</p>
          <p className="font-medium">Phoenix, Arizona, United States.</p>
        </div>
      </CardContent>
    </Card>
  );
}
