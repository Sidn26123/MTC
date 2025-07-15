import { create } from 'zustand';

export const usePolicyStore = create((set) => ({
    termsOfService: {},
    privacyPolicy: {},

    actions: {
        setTermsOfService: (terms) => set(() => ({
            termsOfService: { ...terms }
        })),

        setPrivacyPolicy: (policy) => set(() => ({
            privacyPolicy: { ...policy }
        }))
    }
}));

export const useTermsOfService = () => usePolicyStore((state) => state.termsOfService);
export const usePrivacyPolicy = () => usePolicyStore((state) => state.privacyPolicy);

export const useSetTermsOfService = () => usePolicyStore((state) => state.actions.setTermsOfService);
export const useSetPrivacyPolicy = () => usePolicyStore((state) => state.actions.setPrivacyPolicy);
