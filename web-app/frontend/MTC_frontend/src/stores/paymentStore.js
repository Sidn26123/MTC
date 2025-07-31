import { create } from 'zustand';
const defaultPagination = {
    totalPages: 0,
    pageSize: 10,
    currentPage: 1,
    totalElements: 0,
    data: []
};

const usePaymentStore = create((set) => ({
    paymentHistory: { ...defaultPagination },
    myWallet: [],

    actions: {
        setPaymentHistory: (paginationData) => set(() => ({
            paymentHistory: paginationData
        })),

        setMyWallet: (walletData) => set(() => ({
            myWallet: walletData
        })),

        resetPaymentHistory: () => set(() => ({
            paymentHistory: { ...defaultPagination }
        }))
    }
}));

export const usePaymentHistory = () => usePaymentStore((state) => state.paymentHistory);
export const useSetPaymentHistory = () => usePaymentStore((state) => state.actions.setPaymentHistory);

export const useMyWallet = () => usePaymentStore((state) => state.myWallet);
export const useSetMyWallet = () => usePaymentStore((state) => state.actions.setMyWallet);