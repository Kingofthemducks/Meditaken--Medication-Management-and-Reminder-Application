package com.laszlo.meditaken

import io.supabase.supabase

object SupabaseClient {
    val client = supabase(
        url = "https://ylypyvyscinqhtzxvivh.supabase.co",
        key = "YOUR_ANON_PUBLIC_KEY"
    )
}
