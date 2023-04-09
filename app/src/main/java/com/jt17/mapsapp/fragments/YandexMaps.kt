package com.jt17.mapsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jt17.mapsapp.R
import com.jt17.mapsapp.databinding.FragmentMapsBinding
import com.jt17.mapsapp.databinding.FragmentYandexMapsBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

//
class YandexMaps : Fragment() {
    private var _binding: FragmentYandexMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        MapKitFactory.setApiKey("51b378a5-16a3-446b-95d4-41d199ba8adf")
        MapKitFactory.initialize(requireContext())
        _binding = FragmentYandexMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        mapView = binding.yandexMapView

        mapView.map.move(
            CameraPosition(Point(41.475980, 69.582925), 12.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 300f), null
        )

    }

    override fun onStart() {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}