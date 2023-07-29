package org.armandosalazar.aseapplication.ui.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.armandosalazar.aseapplication.adapter.NotificationsAdapter;
import org.armandosalazar.aseapplication.databinding.FragmentNotificationBinding;
import org.armandosalazar.aseapplication.model.Notification;
import org.armandosalazar.aseapplication.network.NotificationsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    private FragmentNotificationBinding binding;

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        binding.recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(getContext()));

        NotificationsService notificationsService = NotificationsService.retrofit.create(NotificationsService.class);

        Call<List<Notification>> call = notificationsService.getNotifications();

        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                binding.recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewNotifications.setAdapter(new NotificationsAdapter(response.body()));
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {

            }
        });

        return binding.getRoot();
    }
}