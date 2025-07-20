import { Card, CardContent, CardHeader, CardTitle } from "../ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "../ui/avatar";
import { Button } from "../ui/button";
import {
  FacebookIcon,
  TwitterIcon,
  LinkedinIcon,
  InstagramIcon,
  EditIcon,
} from "lucide-react";

export default function ProfileCard() {
  return (
    <Card className="p-6">
      <CardHeader className="flex flex-row items-center justify-between space-y-0 p-0">
        <CardTitle className="text-xl font-semibold">Profile</CardTitle>
      </CardHeader>
      <CardContent className="mt-6 flex flex-col items-start gap-6 p-0 md:flex-row md:items-center">
        <div className="flex items-center gap-4">
          <Avatar className="h-20 w-20">
            <AvatarImage src="/placeholder.svg?height=80&width=80" alt="Musharof Chowdhury" />
            <AvatarFallback>MC</AvatarFallback>
          </Avatar>
          <div className="grid gap-1">
            <h2 className="text-xl font-semibold">Musharof Chowdhury</h2>
            <p className="text-sm text-gray-500 dark:text-gray-400">Team Manager</p>
            <p className="text-sm text-gray-500 dark:text-gray-400">Arizona, United States</p>
          </div>
        </div>
        <div className="flex items-center gap-2 md:ml-auto">
          {[FacebookIcon, TwitterIcon, LinkedinIcon, InstagramIcon].map((Icon, i) => (
            <Button key={i} variant="outline" size="icon" className="rounded-full bg-transparent">
              <Icon className="h-4 w-4" />
              <span className="sr-only">{Icon.name}</span>
            </Button>
          ))}
          <Button variant="outline" className="ml-4 bg-transparent">
            <EditIcon className="mr-2 h-4 w-4" />
            Edit
          </Button>
        </div>
      </CardContent>
    </Card>
  );
}
