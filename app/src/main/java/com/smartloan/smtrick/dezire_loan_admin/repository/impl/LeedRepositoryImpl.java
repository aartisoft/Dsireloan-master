package com.smartloan.smtrick.dezire_loan_admin.repository.impl;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;
import com.smartloan.smtrick.dezire_loan_admin.constants.Constant;
import com.smartloan.smtrick.dezire_loan_admin.models.Invoice;
import com.smartloan.smtrick.dezire_loan_admin.models.LeedsModel;
import com.smartloan.smtrick.dezire_loan_admin.repository.FirebaseTemplateRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.LeedRepository;

import java.util.ArrayList;
import java.util.Map;

public class LeedRepositoryImpl extends FirebaseTemplateRepository implements LeedRepository {
    @Override
    public void readAllLeeds(final CallBack callBack) {
        final Query query = Constant.LEEDS_TABLE_REF;
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<LeedsModel> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            LeedsModel leedsModel = suggestionSnapshot.getValue(LeedsModel.class);
                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }


    @Override
    public void readLeedsByUserIdReport(final Context context, String userId, final CallBack callBack) {
        final Query query = Constant.LEEDS_TABLE_REF.orderByChild("createdBy").equalTo(userId);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<LeedsModel> leedsModelArrayList = new ArrayList<>();
                        int colorCount = 0;
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            LeedsModel leedsModel = suggestionSnapshot.getValue(LeedsModel.class);
                            if (colorCount % 5 == 0)
                                colorCount = 0;
                           // setColor(context, leedsModel, colorCount);
                            colorCount++;
                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }



    @Override
    public void createLeed(LeedsModel leedsModel, final CallBack callBack) {
        DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedsModel.getLeedId());
        fireBaseCreate(databaseReference, leedsModel, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                callBack.onSuccess(object);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }




    @Override
    public void deleteLeed(String leedId, CallBack callback) {
        DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedId);
        fireBaseDelete(databaseReference, callback);
    }

    @Override
    public void updateLeed(String leedId, Map leedMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedId);
        fireBaseUpdateChildren(databaseReference, leedMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                callBack.onSuccess(object);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }


    public void readLeedsByStatus(String status, final CallBack callBack) {
        final Query query = Constant.LEEDS_TABLE_REF.orderByChild("status").equalTo(status);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<LeedsModel> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            LeedsModel leedsModel = suggestionSnapshot.getValue(LeedsModel.class);
                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void readLeedByLeedId(String leedId, final CallBack callBack) {
        final Query query = Constant.LEEDS_TABLE_REF.child(leedId);
        fireBaseReadData(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        DataSnapshot child = dataSnapshot.getChildren().iterator().next();
                        LeedsModel leedsModel = child.getValue(LeedsModel.class);
                        callBack.onSuccess(leedsModel);
                    } else
                        callBack.onSuccess(null);
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }


    @Override
    public void updateLeedDocuments(String leedId, Map leedMap, CallBack callback) {

    }

    @Override
    public void updateLeedHistory(String leedId, Map leedMap, CallBack callback) {

    }


}
