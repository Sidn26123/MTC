import React from "react";
import { Link } from "react-router";

const Alert = ({
  variant,
  title,
  message,
  showLink = false,
  linkHref = "#",
  linkText = "Learn more",
}) => {
  const variantClasses = {
    success: {
      container:
        "border-success-500 bg-success-50 dark:border-success-500/30 dark:bg-success-500/15",
      icon: "text-success-500",
    },
    error: {
      container:
        "border-error-500 bg-error-50 dark:border-error-500/30 dark:bg-error-500/15",
      icon: "text-error-500",
    },
    warning: {
      container:
        "border-warning-500 bg-warning-50 dark:border-warning-500/30 dark:bg-warning-500/15",
      icon: "text-warning-500",
    },
    info: {
      container:
        "border-blue-light-500 bg-blue-light-50 dark:border-blue-light-500/30 dark:bg-blue-light-500/15",
      icon: "text-blue-light-500",
    },
  };

  const icons = {
    success: (
      <svg className="fill-current" width="24" height="24" viewBox="0 0 24 24">
        <path
          fillRule="evenodd"
          clipRule="evenodd"
          d="M3.7 12a8.3 8.3 0 1 1 16.6 0 8.3 8.3 0 0 1-16.6 0zm8.3-10.1a10.1 10.1 0 1 0 0 20.2 10.1 10.1 0 0 0 0-20.2zm3.62 8.84a.9.9 0 1 0-1.27-1.27l-3.16 3.16-1.54-1.54a.9.9 0 1 0-1.27 1.27l2.17 2.17a.9.9 0 0 0 1.27 0l3.8-3.79z"
        />
      </svg>
    ),
    error: (
      <svg className="fill-current" width="24" height="24" viewBox="0 0 24 24">
        <path
          fillRule="evenodd"
          clipRule="evenodd"
          d="M20.35 12a8.35 8.35 0 1 1-16.7 0 8.35 8.35 0 0 1 16.7 0zM12 22.15a10.15 10.15 0 1 0 0-20.3 10.15 10.15 0 0 0 0 20.3zm1-5.675a1 1 0 0 0-2 0 1 1 0 0 0 2 0zM12 6.63a.75.75 0 0 1 .75.75v5.68a.75.75 0 0 1-1.5 0V7.38a.75.75 0 0 1 .75-.75z"
        />
      </svg>
    ),
    warning: (
      <svg className="fill-current" width="24" height="24" viewBox="0 0 24 24">
        <path
          fillRule="evenodd"
          clipRule="evenodd"
          d="M12 3.65a8.35 8.35 0 1 0 0 16.7 8.35 8.35 0 0 0 0-16.7zm0-1.8a10.15 10.15 0 1 0 0 20.3 10.15 10.15 0 0 0 0-20.3zM11 7.53a1 1 0 0 1 2 0 1 1 0 0 1-2 0zm1 9.85a.75.75 0 0 1-.75-.75v-5.68a.75.75 0 0 1 1.5 0v5.68a.75.75 0 0 1-.75.75z"
        />
      </svg>
    ),
    info: (
      <svg className="fill-current" width="24" height="24" viewBox="0 0 24 24">
        <path
          fillRule="evenodd"
          clipRule="evenodd"
          d="M12 3.65a8.35 8.35 0 1 0 0 16.7 8.35 8.35 0 0 0 0-16.7zm0-1.8a10.15 10.15 0 1 0 0 20.3 10.15 10.15 0 0 0 0-20.3zM11 7.52a1 1 0 0 1 2 0 1 1 0 0 1-2 0zm1 9.85a.75.75 0 0 1-.75-.75v-5.68a.75.75 0 0 1 1.5 0v5.68a.75.75 0 0 1-.75.75z"
        />
      </svg>
    ),
  };

  return (
    <div
      className={`rounded-xl border p-4 ${variantClasses[variant].container}`}
    >
      <div className="flex items-start gap-3">
        <div className={`-mt-0.5 ${variantClasses[variant].icon}`}>
          {icons[variant]}
        </div>
        <div>
          <h4 className="mb-1 text-sm font-semibold text-gray-800 dark:text-white/90">
            {title}
          </h4>
          <p className="text-sm text-gray-500 dark:text-gray-400">{message}</p>
          {showLink && (
            <Link
              to={linkHref}
              className="inline-block mt-3 text-sm font-medium text-gray-500 underline dark:text-gray-400"
            >
              {linkText}
            </Link>
          )}
        </div>
      </div>
    </div>
  );
};

export default Alert;
